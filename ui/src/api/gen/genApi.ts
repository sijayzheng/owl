import type { GenTablePageQuery } from '@/types/gen/GenTable'

export const genApi = {
  /**
   * 查询代码生成表列表
   */
  page(query: GenTablePageQuery) {
    return request.page<Array<GenTable>>('/gen/page', query)
  },
  /**
   * 查询代码生成表详情
   */
  getById(id: number) {
    return request.get<GenTable>(`/gen/getById/${id}`)
  },
  /**
   * 修改代码生成表
   */
  update(data: GenTable) {
    return request.post<boolean>('/gen/update', data)
  },
  /**
   * 删除代码生成表
   */
  remove(ids: number[]) {
    return request.post<boolean>(`/gen/remove`, ids)
  },
  /**
   * 查询库中所有的表
   */
  listDbTable(query: GenTableQuery) {
    return request.get<Array<GenTable>>('/gen/listDbTable', query)
  },
  /**
   * 导入SQL并生成表
   */
  importTable(tableName: string) {
    return request.post<boolean>('/gen/import', { tableName })
  },
  /**
   * 生成代码
   */
  generateCode(tableId: number) {
    return request.post<boolean>(`/gen/generate/${tableId}`)
  },
}
