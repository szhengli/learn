import request from '@/utils/request'

export function podlog(params) {
  return request({
    url: '/demo/podlog/',
    method: 'get',
    params
  })
}
