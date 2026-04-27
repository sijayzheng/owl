// 匹配views里面所有的.vue文件
import type {RouteRecordRaw} from 'vue-router'
import router, {staticRouters} from "@/router";
import Layout from "@/layout/layout.vue";
import {Component, defineComponent, h} from 'vue';

export function createCustomNameComponent(loader: () => Promise<any>, name?: string): () => Promise<Component> {
  let component: Component | null = null;

  const load = async () => {
    try {
      const {
        default: loadedComponent
      } = await loader();
      component = loadedComponent;
    } catch (error) {
      console.error(`Cannot resolve component ${name}, error:`, error);
    }
  };

  return async () => {
    if (!component) {
      await load();
    }

    return Promise.resolve(
      defineComponent({
        name,
        render() {
          return h(component as Component);
        }
      })
    );
  };
}

// 匹配views里面所有的.vue文件
const modules = import.meta.glob('@/views/**/*.vue');
const loadView = (view: any, name?: string) => {
  let res: () => Promise<Component>;
  for (const path in modules) {
    const viewsIndex = path.indexOf('/views/');
    let dir = path.substring(viewsIndex + 7);
    dir = dir.substring(0, dir.lastIndexOf('.vue'));
    if (dir === view) {
      res = createCustomNameComponent(modules[path], name);
      return res;
    }
  }
  return res;
};
export const usePermissionStore = defineStore('permission', () => {
  // 让菜单在未加载动态路由前也能正常显示，初始化为静态路由
  const routes = ref<RouteRecordRaw[]>(staticRouters);
  const getSideBarRoutes = () => {
    return routes.value.filter(item => {
      return !item.meta?.hidden
    })
  }

  const generateRoutes = async (): Promise<RouteRecordRaw[]> => {
    // 仅取 data[0] 的 children 作为动态路由输入
    const rawRoutes = await commonApi.getRoutes()
    const asyncRoutes = (!rawRoutes || rawRoutes.length === 0) ? [] : rawRoutes.map(rt => convertRoute(rt))
    asyncRoutes.forEach((route) => {
      router.addRoute(route);
    });
    const finalRoutes: RouteRecordRaw[] = staticRouters.concat(asyncRoutes);
    routes.value = finalRoutes;
    return new Promise<RouteRecordRaw[]>((resolve) => resolve(finalRoutes));
  };

  /**
   * 遍历后台传来的路由字符串，转换为组件对象
   * @param asyncRouterMap 后台传来的路由字符串
   * @param lastRouter 上一级路由
   * @param type 是否是重写路由
   */
  return {
    routes,
    generateRoutes,
    getSideBarRoutes,
  };
});

/** 将单个后端 Route 转换为 Vue Router 的 RouteRecordRaw */
function convertRoute(rt: Route): RouteRecordRaw {
  const raw: any = {
    path: `/${rt.path}`,
    name: rt.name,
    // 将 hidden 信息放在 meta 中，供路由守卫/导航展示使用
    meta: rt.meta ?? {},
  }
  // 将 hidden 显式映射到 meta.hidden 以兼容现有逻辑
  if (typeof rt.hidden === 'boolean') {
    raw.meta = {...(raw.meta || {}), hidden: rt.hidden}
  }
  // 路由组件使用动态导入
  raw.component = rt.component === 'Layout' ? Layout : loadView(rt.component, raw.name)
  if (rt.children && rt.children.length > 0) {
    raw.children = rt.children.map(convertRoute)
  }
  return raw
}


