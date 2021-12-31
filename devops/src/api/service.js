import request from '@/utils/request'

export function service_add(data) {
  return request({
    url: '/demo/service_add/',
    method: 'post',
    data
  })
}

export function service_approve(data) {
  return request({
    url: '/demo/service_approve/',
    method: 'post',
    data
  })
}


export function get_serviceList(params) {
  return request({
    url: '/demo/get_serviceList/',
    method: 'get',
    params
  })
}
