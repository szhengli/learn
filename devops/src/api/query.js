import request from '@/utils/request'

export function get_all_branches(params) {
  return request({
    url: '/demo/get_all_branches/',
    method: 'get',
    params
  })
}

export function get_release_records(params) {
  return request({
    url: '/demo/get_release_records/',
    method: 'get',
    params
  })
}
