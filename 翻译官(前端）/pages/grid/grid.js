Page({
  data: {
    grids: [0, 1, 2, 3, 4, 5, 6, 7],
    tag1: ["Lifestyle", "World", "Travel", "Culture", "Opinion", "Business", "China", "Sport", "Food"],
    // tag2: {
    //   China: ["News", "Scoiety", "Innovation", "HK/Taiwan/Macao", "Cover Story", "Environment", "Health", "Millitary"],
    //   World: ["Asia-Pacific", "Americas", "Europe", "Middle East", "Africa", "China-US", "China-Europe", "China-Japan", "China-Africa"],
    //   Business: ["Macro", "Companies", "industries", "Technology", "Motoring",],
    //   Lifestyle: ['Fashion', 'Celebrities', 'People', 'Food', 'Health', 'photo'],
    //   Culture: ['Art', 'Music&Theater', 'Film&TV', 'Books', 'Heritage', 'Events&Festivals', 'People', 'Cultural Exchange', 'Photo'],
    //   Travel: ['News', 'City Tours', 'Guides and Tips', 'My FootPrints', 'Around the World', 'Features', 'Photo'],
    //   Sprot: ['Score', 'Baskeball', 'Volleyball', 'Tennis', 'Golf', 'Track and Field', 'Swimming', 'Winter Sports', 'China'],
    //   Opinion: ['Editorials', 'Op-Ed Contributors', 'Columnists', 'From the Press', 'Opinion Line', 'From the Readers'],
    // },
    isSelected: [],
    selected: []
  },

  onLoad: function (option) {
    var buf = new Array()
    for (var i = 0; i < this.data.tag1.length; ++i) {
      buf.push(false)
      
    }
    this.setData({ isSelected: buf })
    console.log(buf)
  },

  select: function (e) {
    console.log(e)
    var that = this
    var index = e.currentTarget.dataset.number;
    var isSelected = that.data.isSelected
    isSelected[index] = !isSelected[index]
    // that.setData({ isSelected: isSelected })

    var selected = that.data.selected
    if(isSelected[index]) {
      selected.push(that.data.tag1[index])
    } else {
      var removeIndex = selected.indexOf(that.data.tag1[index])
      selected.splice(removeIndex, 1)
    }
    that.setData({
      selected: selected,
      isSelected: isSelected
    })
    console.log(that.data.selected)
    if (isSelected.indexOf(true) >= 0) {
      that.setData({ showBtn: true })
    }
  },

  confirm: function (e) {
    var that = this
    var app = getApp()
    var _id = app.globalData._id
    console.log(_id)
    wx.request({
      url: 'http://127.0.0.1:8080/WeChatWeb/EditTags',
      data: {
        tags: JSON.stringify(that.data.selected) ,
        _id: _id
      },
      success: res => {
        console.log(res)
      }
    })
    wx.showToast({
      title: '设置成功',
      icon: 'success',
    })


  },

  skip: function (e) {
    wx.navigateBack({})
  }


});