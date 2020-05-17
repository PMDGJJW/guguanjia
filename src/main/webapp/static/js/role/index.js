let vm = new Vue({
    el: '.main-content',
    data(){
        return{
            params:{
                oid:'',
                dataScope:'',
                name:'',
                remarks:''
            },
            pageInfo:{
                pageNum:1,
                pageSize:5
            },
            name: '全部',
            setting:{
                data:{
                    key:{
                        title: 'title',
                    },
                    simpleData: {
                        enable:true ,//设置节点数组使用一维数组格式
                        pIdKey:'parentId'  //设置父id属性名
                    }
                },
                callback:{
                    onClick:this.onClick
                },
                view:{/*显示处理回调设置*/
                    fontCss: this.fontCss
                }
            },
            showTree(flag){
                this.isShow = flag;
                this.flag = !this.flag
            },
            onClick(event, treeId, treeNode) {
                // console.log(event);
                // console.log(treeId);
                this.name=treeNode.name;
                if (treeNode.id!=0) {
                    this.params.oid = treeNode.id
                }

            },
            initTree() {
                axios({
                    url:'manager/Work/office',
                    method:'post',
                }).then( resp =>{
                    this.nodes = resp.data.obj;
                    //动态生成一个根节点
                    this.nodes[this.nodes.length]={id:0,name:'全部机构',open:true};
                    let zTree = $.fn.zTree.init($('#pullDownTreeone'), this.setting, this.nodes);
                } ).catch( error =>{
                    console.log(error.message)
                } )

            },
            search(){
                let zTreeObj = $.fn.zTree.getZTreeObj("pullDownTreeone");
                let nodesbyParamFuzzy = zTreeObj.getNodesByParamFuzzy("name", this.name, null);
                let treeNodes = zTreeObj.transformToArray(zTreeObj.getNodes());
                for (let i = 0; i < treeNodes.length; i++) {
                    treeNodes[i].hightLight=false;
                    zTreeObj.updateNode(treeNodes[i]);
                }
                //将查找到的节点高亮属性设置为true
                for (let i in nodesbyParamFuzzy) {
                    nodesbyParamFuzzy[i].hightLight=true;//设置高亮属性
                    zTreeObj.updateNode(nodesbyParamFuzzy[i])//更新树对象上的节点信息，会自动触发显示回调
                }
            },

            fontCss(treeId,treeNode) {
                /*根据高亮标记设置高亮红色*/
                return treeNode.hightLight?{color:'red'}:{color:'black'};
            },
        }

    },
    methods: {
        toRoleSave(rid,name,oname,dataScope){
                layer.rid = rid;//数据，绑定到layer上，传递给子窗口
                layer.name = name;
                layer.officeName=oname;
                layer.dataScope=dataScope;
                // console.log(layer)
                let index = layer.open({
                    type:2,
                    title:'用户角色授权',
                    content:'manager/role/toRoleSave',
                    area:['90%','90%']
                });
        },
        selectPage(pageNum,pageSize){
            axios({
                url:`manager/role/selectPage/${pageNum}/${pageSize}`,
                data:this.params,
                method:'post'
            }).then(resp=>{
                this.pageInfo= resp.data.obj;

            }).catch(error=>{
                layer.msg(error.message)
            })
        },
        managerUsers:function(rid,name){//跳转到用户角色授权页面 role-user
            layer.rid = rid;//数据，绑定到layer上，传递给子窗口
            layer.name = name;
            // console.log(layer)
            let index = layer.open({
                type:2,
                title:'用户角色授权',
                content:'manager/role/toRoleUser',
                area:['80%','80%']
            });
        },

    },
    created() {
        this.selectPage(1,this.pageInfo.pageSize);

    },
    mounted(){
        this.initTree();
    }
})
