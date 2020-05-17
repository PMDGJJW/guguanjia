let vm = new Vue({
    el: '.main-content',
    data(){
        return{
            isShow:false,
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
            nodes:'',
            flag:false,
            name: '全部',
            pageInfo:{
                pageNum:1,
                pageSize:5
            },
            params:{
                officeId:'',
                name:'',
                type:''
            }
        }
    },
    methods: {
        selectPage(pageNum,pageSize){
            axios({
                url: `manager/examine/selectPage/${pageNum}/${pageSize}`,
                method: 'post',
                data:this.params
            }).then(resp => {
                this.pageInfo=resp.data.obj
            }).catch(error => {
                console.log(error.message)
            });
        },
        selectAll(pageNum, pageSize) {
            this.params={
                officeId:'',
                name:'',
                type:''
            };
            axios({
                url: `manager/examine/selectPage/${pageNum}/${pageSize}`,
                method: 'post',
                data: this.params
            }).then(resp => {
                this.pageInfo = resp.data.obj
            }).catch(error => {
                console.log(error.message)
            });
        },
        showTree(flag){
            this.isShow = flag;
            this.flag = !this.flag
        },
        onClick(event, treeId, treeNode) {
            // console.log(event);
            // console.log(treeId);
            // console.log(treeNode);
            this.name=treeNode.name;

            if (treeNode.id!=0) {
                console.log(treeNode.id);
                this.params.officeId = treeNode.id
            }

        },
        initTree() {
            axios({
                url:'manager/examine/office',
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
        }
    },
    created() {
        this.selectPage(1,this.pageInfo.pageSize)
    }
    , mounted(){
        this.initTree()
    }
})
