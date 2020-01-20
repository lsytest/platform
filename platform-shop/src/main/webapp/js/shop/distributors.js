$(function () {
    $("#jqGrid").Grid({
        url: '../distributors/list',
        colModel: [
			{label: 'id', name: 'id', index: 'id', key: true, hidden: true},
			{label: '会员ID', name: 'memberId', index: 'member_id', width: 80},
			{label: '分销商名称', name: 'name', index: 'name', width: 80},
			{label: '分销商描述', name: 'description', index: 'description', width: 80},
			{label: '分销商图标', name: 'image', index: 'image', width: 80},
			{label: '分销商地址', name: 'address', index: 'address', width: 80},
			{label: '购买指数', name: 'puchaseNum', index: 'puchase_num', width: 80},
			{label: '粉丝数', name: 'fansNum', index: 'fans_num', width: 80},
			{label: '父级ID', name: 'parentId', index: 'parent_id', width: 80},
			{label: '创建时间', name: 'createTime', index: 'create_time', width: 80}]
    });
});

let vm = new Vue({
	el: '#rrapp',
	data: {
        showList: true,
        title: null,
		distributors: {},
		ruleValidate: {
			name: [
				{required: true, message: '名称不能为空', trigger: 'blur'}
			]
		},
		q: {
		    name: ''
		}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function () {
			vm.showList = false;
			vm.title = "新增";
			vm.distributors = {};
		},
		update: function (event) {
            let id = getSelectedRow("#jqGrid");
			if (id == null) {
				return;
			}
			vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
            let url = vm.distributors.id == null ? "../distributors/save" : "../distributors/update";
            Ajax.request({
			    url: url,
                params: JSON.stringify(vm.distributors),
                type: "POST",
			    contentType: "application/json",
                successCallback: function (r) {
                    alert('操作成功', function (index) {
                        vm.reload();
                    });
                }
			});
		},
		del: function (event) {
            let ids = getSelectedRows("#jqGrid");
			if (ids == null){
				return;
			}

			confirm('确定要删除选中的记录？', function () {
                Ajax.request({
				    url: "../distributors/delete",
                    params: JSON.stringify(ids),
                    type: "POST",
				    contentType: "application/json",
                    successCallback: function () {
                        alert('操作成功', function (index) {
                            vm.reload();
                        });
					}
				});
			});
		},
		getInfo: function(id){
            Ajax.request({
                url: "../distributors/info/"+id,
                async: true,
                successCallback: function (r) {
                    vm.distributors = r.distributors;
                }
            });
		},
		reload: function (event) {
			vm.showList = true;
            let page = $("#jqGrid").jqGrid('getGridParam', 'page');
			$("#jqGrid").jqGrid('setGridParam', {
                postData: {'name': vm.q.name},
                page: page
            }).trigger("reloadGrid");
            vm.handleReset('formValidate');
		},
        reloadSearch: function() {
            vm.q = {
                name: ''
            }
            vm.reload();
        },
        handleSubmit: function (name) {
            handleSubmitValidate(this, name, function () {
                vm.saveOrUpdate()
            });
        },
        handleReset: function (name) {
            handleResetForm(this, name);
        }
	}
});