Page({
  data: {
    trans_result: '',
    from: 'auto',
    to: 'en'
  },

  onLoad() {
    this.ctx = wx.createCameraContext()
  },

  takePhoto() {
    var that = this
    wx.showLoading({
      title: '正在识别',
    })

    this.ctx.takePhoto({
      quality: 'low',
      success: (res) => {
        console.log(res)
        console.log(res.height)
        console.log(res.width)
        var height = res.height
        var width = res.width
        if(height == undefined) height = 920
        if(width == undefined) width = 720
        wx.uploadFile({
          url: 'http://127.0.0.1:8080/WeChatWeb/Photo',
          filePath: res.tempImagePath,
          name: 'file',
          header: {
            'content-type': 'multipart/form-data' 
          },
          formData: {
            height: height,
            width: width,
            _from: that.data.from,
            to: that.data.to
          },
          success: res => {
            console.log(res)
            var data = JSON.parse(res.data)
            console.log(JSON.parse(res.data))
            wx.hideLoading()
            if(data.resRegions.length <= 0 ){
              
              wx.showToast({
                title: '换个姿势再来一遍吧',
              })
            } else {
              var resRegions = JSON.stringify(data.resRegions)
              wx.redirectTo({
                url: '/pages/ocr/ocr?resRegions=' + resRegions,
              })
            }

            // var str = getString(JSON.parse(res.data))

            
            // translate(str)
            // wx.request({
            //   url: 'http://127.0.0.1:8080/WeChatWeb/Translate_B',
            //   data: {
            //     q: str
            //   },
            //   success: function (res) {
            //     that.setData({
            //       trans_result: res.data.trans_result
            //     })

            //     wx.redirectTo({
            //       url: '/pages/ocr/ocr?trans_result=' + JSON.stringify(that.data.trans_result),
            //     })
            //   }
            // })           
          }
        })
      }
    })
  },
  startRecord() {
    this.ctx.startRecord({
      success: (res) => {
        console.log('startRecord')
      }
    })
  },
  stopRecord() {
    this.ctx.stopRecord({
      success: (res) => {
        this.setData({
          src: res.tempThumbPath,
          videoSrc: res.tempVideoPath
        })
      }
    })
  },
  error(e) {
    console.log(e.detail)
  }
})

function getString(data) {
  var text = ''
  var regions = data.Result.regions
  for (var i = 0; i < regions.length; ++i) {
    var lines = regions[i].lines
    for (var j = 0; j < lines.length; ++j) {
      var words = lines[j].words
      for (var k = 0; k < words.length; ++k) {
        text += encodeURI(words[k].word)
        text += ' '
      }
      text += '\n'
    }
  }
  
  return text
}

// function getString(data) {
//   var result = []
//   var text = ''
//   var regions = data.Result.regions
//   for (var i = 0; i < regions.length; ++i) {
//     var lines = regions[i].lines
//     for (var j = 0; j < lines.length; ++j) {
//       var words = lines[j].words
//       for (var k = 0; k < words.length; ++k) {
//         text += encodeURI(words[k].word) 
//         text += ' '
//       }      
//       result.push(translate(text))
//       text = ''
//     }
//   }
//   return result
// }

// function translate(str) {
//   var that = this
//   wx.request({
//     url: 'http://127.0.0.1:8080/WeChatWeb/Translate_B',
//     data: {
//       q: str
//     },
//     success: function (res) {
//       that.setData({
//         trans_result: res.data.trans_result
//       })
//     }
//   })
// }