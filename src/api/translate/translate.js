import config from '@/config/config.env'
import { get, postForm, exportExcel } from '@/utils/request'

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

export function exportToExcel(params) {
  return exportExcel({
    baseURL: config.commonAPI,
    url: '/export',
    params
  })
}

export function edit(params) {
  return get({
    baseURL: config.commonAPI,
    url: '/update',
    params
  })
}

export function add(params) {
  return get({
    baseURL: config.commonAPI,
    url: '/add',
    params
  })
}

export function exportToJson(params) {
  return get({
    baseURL: config.commonAPI,
    url: '/exportJS',
    params
  })
}

export function getLangList() {
  return get({
    baseURL: config.commonAPI,
    url: '/getLang'
  })
}

export function appendCol(params) {
  return get({
    baseURL: config.commonAPI,
    url: '/append',
    params
  })
}

export function deleteDocument(params) {
  return postForm({
    baseURL: config.commonAPI,
    url: '/deleteDocument',
    params
  })
}

export function deleteWords(params) {
  return get({
    baseURL: config.commonAPI,
    url: '/deleteWords',
    params
  })
}
