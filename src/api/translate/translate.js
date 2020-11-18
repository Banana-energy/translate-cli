import config from '@/config/config.env'
import { get, postForm } from '@/utils/request'

export function getDocument() {
  return get({
    baseURL: config.commonAPI,
    url: '/getDocument'
  })
}

export function loadJs(params) {
  return postForm({
    baseURL: config.commonAPI,
    url: '/load',
    params
  })
}

export function getColumns() {
  return get({
    baseURL: config.commonAPI,
    url: '/getColumn'
  })
}

export function getTranslateList(params) {
  return get({
    baseURL: config.commonAPI,
    url: '/getList',
    params
  })
}

