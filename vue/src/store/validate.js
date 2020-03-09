const actions = {
  //校验电话格式
  checkPhone ({commit}, object) {
    if (object.value === '' || object.value === null) {
      object.callback()
    }
    let reg = /^1[0-9]{10}$/
    if (reg.test(object.value)) {
      object.callback()
    } else {
      object.callback(new Error('手机格式不正确'))
    }
  },
  // 校验必填项与空格
  checkRequiredAndSpace ({commit}, object) {
    let temp = object.value.toString()
    if (object.value === '' || object.value === null) {
      object.callback('不能为空')
    } else if (object.value.indexOf(' ') >= 0) {
      object.callback('输入字符中不能有空格')
    } else if (temp.length > 25) {
      object.callback('输入过长')
    } else {
      object.callback()
    }
  },
  // 校验正整数 非必填项
  checkPositiveInteger ({commit}, object) {
    let regu = /^[1-9]\d*$/
    if (object.value !== '') {
      if (!regu.test(object.value)) {
        object.callback('必须为正整数')
      } else {
        object.callback()
      }
    } else {
      object.callback()
    }
  },
  // 校验非负整数 非必填项
  checkNonnegativeInteger ({commit}, object) {
    let regu = /^(0|[1-9]\d*)$/
    if (object.value !== '') {
      if (!regu.test(object.value)) {
        object.callback('必须为非负整数')
      } else {
        object.callback()
      }
    } else {
      object.callback()
    }
  },

  // 校验正整数与空格 必填项
  checkPositiveIntegerAndSpace ({commit}, object) {
    if (object.value === '' || object.value === null) {
      object.callback('不能为空')
    } else if (isNaN(object.value) || !/^[0-9]*[1-9][0-9]*$/.test(object.value)) {
      object.callback('请输入正整数')
    } else if (object.value.toString().indexOf(' ') >= 0) {
      object.callback('输入字符中不能有空格')
    } else {
      object.callback()
    }
  },

  // 校验只能是数字、汉字和字母与空格与输入过长
  checkNumberOrSinogramOrLetterAndSpace ({commit}, object) {
    let temp = object.value.toString()
    let regu = /^[A-Za-z0-9\u4e00-\u9fa5]+$/
    if (object.value === '' || object.value === null) {
      object.callback('不能为空')
    } else if (object.value.indexOf(' ') >= 0) {
      object.callback('输入字符中不能有空格')
    } else if (temp.length > 25) {
      object.callback('输入过长')
    } else if (!regu.test(object.value)) {
      object.callback('只能是数字、汉字和字母')
    } else {
      object.callback()
    }
  }
}

export default {
  actions
}
