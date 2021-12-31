import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/demo/api-token-auth/',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/demo/user_info/',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/demo/logout/',
    method: 'post'
  })
}
