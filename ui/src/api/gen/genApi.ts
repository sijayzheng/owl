export const GenApi = {
  /**
   * 查询代码生成表列表
   */
  list(query: GenTableQuery) {
    return request.get<Array<GenTable>>('/gen/list', query)
  },
  /**
   * 查询代码生成表详情
   */
  getById(id: number) {
    return request.get<GenTable>(`/gen/${id}`)
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
  remove(id: number) {
    return request.post<boolean>(`/gen/remove/${id}`)
  },
  /**
   * 查询库中所有的表
   */
  listDbTable(query: GenTableQuery) {
    return request.get<Array<GenTable>>('/gen/list-db-table', query)
  },
  /**
   * 导入SQL并生成表
   */
  importData(tables: string[]) {
    return request.post<boolean>('/gen/import', tables)
  },
  /**
   * 生成代码
   */
  generateCode(tableId: number) {
    return request.post<boolean>(`/gen/generate/${tableId}`)
  },
}
