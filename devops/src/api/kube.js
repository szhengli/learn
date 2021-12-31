import request from '@/utils/request'

export function get_all_namespaces(params) {
  return request({
    url: '/demo/get_all_namespaces/',
    method: 'get',
    params
  })
}

export function get_podnames(params) {
  return request({
    url: '/demo/get_podnames/',
    method: 'get',
    params
  })
}
