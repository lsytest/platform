const util = require('../../utils/util.js');
const api = require('../../config/api.js');
const user = require('../../services/user.js');

//获取应用实例
const app = getApp()
Page({
  data: {
    newGoods: [],
    hotGoods: [],
    topics: [],
    brands: [],
    floorGoods: [],
    banner: [],
    channel: []
  },
  onShareAppMessage: function () {
    return {
      title: 'NideShop',
      desc: '雅安尤佳微信小程序商城',
      path: '/pages/index/index'
    }
  },onPullDownRefresh(){
	  	// 增加下拉刷新数据的功能
	    var self = this;
	    this.getIndexData();
 },
  getIndexData: function () {
    let that = this;
    var data = new Object();
    /**
    util.request(api.IndexUrlNewGoods).then(function (res) {
      if (res.errno === 0) {
        data.newGoods= res.data.newGoodsList
      that.setData(data);
      }
    });
    util.request(api.IndexUrlHotGoods).then(function (res) {
      if (res.errno === 0) {
        data.hotGoods = res.data.hotGoodsList
        that.setData(data);
      }
    });
    util.request(api.IndexUrlTopic).then(function (res) {
      if (res.errno === 0) {
        data.topics = res.data.topicList
      that.setData(data);
      }
    });
    util.request(api.IndexUrlBrand).then(function (res) {
      if (res.errno === 0) {
        data.brand = res.data.brandList
      that.setData(data);
      }
    });
    */
    var indexUrlDistrabutorBanner = api.IndexUrlDistrabutorBanner + "/1";
    util.request(indexUrlDistrabutorBanner,{},'GET').then(function(res){
      if (res.errno===0){
        data.distrabutorInfo = res.data.list;
        that.setData(data);
      }
    });
    util.request(api.GoodsList).then(function (res) {
      if (res.errno === 0) {
        data.goodsList = res.data.goodsList
        that.setData(data);
      }
    });
    /**
    util.request(api.IndexUrlBanner).then(function (res) {
      if (res.errno === 0) {
        data.banner = res.data.banner
      that.setData(data);
      }
    });
    */
    util.request(api.IndexUrlChannel).then(function (res) {
      if (res.errno === 0) {
        data.channel = res.data.channel
        that.setData(data);
      }
    });
  },
  onLoad: function (options) {
    this.getIndexData();
  },
  onReady: function () {
    // 页面渲染完成
  },
  onShow: function () {
    // 页面显示
  },
  onHide: function () {
    // 页面隐藏
  },
  onUnload: function () {
    // 页面关闭
  },
})
// 上拉刷新分页