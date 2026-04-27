export const usePageLoading = () => {
  const loadStart = () => {
    const appStore = useAppStore();
    appStore.pageLoading = true;
  };

  const loadDone = () => {
    const appStore = useAppStore();
    appStore.pageLoading = false;
  };

  return {
    loadStart,
    loadDone,
  };
};
