import axios from 'axios'
import qs from 'qs'
import { Message } from 'view-design'

const service = axios.create({
  baseURL: '', // url = base url + request url
  withCredentials: 'none',
  timeout: 5 * 60 * 1000 // request timeout
})

service.interceptors.request.use(
  config => {
    // const accessToken = getAuthInfoByKey('accessToken')
    // if (accessToken) {
    //   config.headers['Authorization'] = `Bearer ${accessToken}`
    // }
    // 处理 post 类型为 application/x-www-form-urlencoded 的参数
    if (
      config.method === 'post' &&
      config.headers['content-type'] === 'application/x-www-form-urlencoded'
    ) {
      config.data = qs.stringify(config.data)
    }
    // 处理 get 请求的 data 参数
    if (config.method === 'get' && config.data) {
      config.url += '?' + qs.stringify(config.data)
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

service.interceptors.response.use(
  response => {
    // 导出 excel 处理
    if (response.config.isExportExcel) {
      const data = response.data
      const contentType = response.headers['content-type']
      let fileName
      if (response.headers['content-disposition']) {
        fileName = decodeURI(
          response.headers['content-disposition'].match(/filename=(.*)/)[1]
        )
      } else {
        fileName = `${new Date().getFullYear()}-${new Date().getMonth()}-${new Date().getDay()}`
      }
      exportResponseData(data, contentType, fileName)
      return
    }

    const res = response.data || response.table
    const code = +res.resultCode

    // 不成功
    if (code !== 200) {
      // 缺少访问令牌
      if (code === 10001) {
        // store.dispatch('user/resetToken').then(() => {
        //   location.reload()
        // })
        // 访问令牌已失效
      } else if (code === 10002) {
        // 如果存储的有 refreshToken，尝试通过 refreshToken 进行免登录
        // store.dispatch('user/resetToken').then(() => {
        //   location.reload()
        // })
      } else {
        Message.error({
          content: res.message || 'Error',
          background: true,
          duration: 5
        })
      }
      return Promise.reject(new Error(res.msg || 'Error'))
    } else {
      return res
    }
  },
  error => {
    Message.error({
      content: error.message || '服务器开小差了，请稍后再试!',
      background: true,
      duration: 5
    })
    return Promise.reject(error)
  }
)

export function exportResponseData(data, contentType, fileName) {
  const a = window.document.createElement('a')
  a.href = window.URL.createObjectURL(new Blob([data], { type: contentType }))
  a.download = fileName
  document.body.appendChild(a)
  a.click()
  document.body.removeChild(a)
}

export const get = config => {
  return service({
    method: 'get',
    ...config
  })
}

/**
 * post 提交 JSON 数据
 * @param config {object} 请求配置，参考 get
 */
export const postJSON = config => {
  return service({
    method: 'post',
    headers: {
      'content-type': 'application/json'
    },
    ...config
  })
}

/**
 * post 提交 form 数据
 * @param config {object} 请求配置，参考 get
 */
export const postForm = config => {
  return service({
    method: 'post',
    headers: {
      'content-type': 'application/x-www-form-urlencoded'
    },
    ...config
  })
}

/**
 * post 提交 formData 数据
 * @param config {object} 请求配置，参考 get
 */
export const postFormData = config => {
  return service({
    method: 'post',
    headers: {
      'content-type': 'multipart/form-data'
    },
    ...config
  })
}

/**
 * 导出 excel 数据
 */
export const exportExcel = config => {
  return service({
    method: 'get',
    isExportExcel: true,
    responseType: 'blob',
    ...config
  })
}
