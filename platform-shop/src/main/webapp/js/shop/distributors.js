$(function () {
	initialPage();
	getGrid();
});

function initialPage() {
	$(window).resize(function () {
		TreeGrid.table.resetHeight({height: $(window).height() - 100});
	});
}


function getGrid() {
	var colunms = TreeGrid.initColumn();
	var table = new TreeTable(TreeGrid.id, '../distributors/queryAll', colunms);
	table.setExpandColumn(0);
	table.setIdField("id");
	table.setCodeField("id");
	table.setParentCodeField("parentId");
	table.setExpandAll(false);
	table.setHeight($(window).height() - 100);
	table.init();
	TreeGrid.table = table;
}
var TreeGrid = {
	id: "jqGrid",
	table: null,
	layerIndex: -1
};

/**
 * 初始化表格的列
 */
TreeGrid.initColumn = function () {
	//, edittype: 'select', formatter:'select',  editoptions:{value:'1:未审核;2:审核成功;3:审核失败'}
	/*
	* , formatter: function (value) {
				return transDate(value, 'yyyy-MM-dd hh:mm:sss');
			}*/
	var columns = [
		{field: 'selectItem', radio: true},
		{title: '名称', field: 'name', align: 'center', valign: 'middle', width: '200px'},
		{title: '图标', field: 'image', align: 'center', valign: 'middle', width: '100px'},
		{title: '描述', field: 'description', align: 'center', valign: 'middle', width: '200px'},
		{title: '地址', field: 'address', align: 'center', valign: 'middle', width: '200px'},
		{title: '创建时间', field: 'createTime', align: 'center', valign: 'middle', width: '200px',
			formatter: function (item) {
				return transDate(item.createTime, 'yyyy-MM-dd hh:mm:sss');
			}
		},
		{title: '状态', field: 'state', align: 'center', valign: 'middle', width: '200px',
			formatter: function (item) {
				return item.state==1?'未审核':item.state==2?'审核成功':'审核拒绝';
			}
		}
	];
	return columns;
};

var vm = new Vue({
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
		del: function (event) {
			var menuIds = TreeGrid.table.getSelectedRow(), ids = [];
			if (menuIds.length == 0) {
				iview.Message.error("请选择一条记录");
				return;
			}
			confirm('确定要删除选中的记录？', function () {
				$.each(menuIds, function (idx, item) {
					ids[idx] = item.id;
				});
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
		updateState: function(event) {
			// var message = (event==2?'审核成功':'审核失败');
			var menuIds = TreeGrid.table.getSelectedRow(), ids = [];
			if (menuIds.length == 0) {
				iview.Message.error("请选择一条记录");
				return;
			}
			$.each(menuIds, function (idx, item) {
				ids[idx] = item.id;
			});
			var items = {"ids": ids, 'state': event};
			Ajax.request({
				url: "../distributors/updateState",
				params: JSON.stringify(items),
				type: "POST",
				contentType: "application/json",
				successCallback: function () {
					alert("操作成功", function (index) {
						vm.reload();
					});
				}
			});
		},
		query: function () {
			vm.reload();
		},
		reload: function (event) {
			vm.showList = true;
			TreeGrid.table.refresh();
		}
	}
});

/*

var vm = new Vue({
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
			var id = getSelectedRow("#jqGrid");
			if (id == null) {
				return;
			}
			vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)
		},

		updateState: function(event) {
			var id = getSelectedRow("#jqGrid");
			if (id == null) {
				alert('至少选择一个')
				return;
			}
			Ajax.request({
				url: "../distributors/updateState",
				params: JSON.stringify(ids),
				type: "POST",
				contentType: "application/json",
				successCallback: function () {
					alert('操作成功', function (index) {
						vm.reload();
					});
				}
			});
		},

		saveOrUpdate: function (event) {
			var url = vm.distributors.id == null ? "../distributors/save" : "../distributors/update";
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
			var ids = getSelectedRows("#jqGrid");
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
			var page = $("#jqGrid").jqGrid('getGridParam', 'page');
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
});*/
