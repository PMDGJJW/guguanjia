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
                startDate:'',
                endDate:'',
                status:''

            }
        }
    },
    methods: {
        selectPage(pageNum,pageSize){
            axios({
                url: `manager/Work/selectPage/${pageNum}/${pageSize}`,
                method: 'post',
                data:this.params
            }).then(resp => {
                this.pageInfo = resp.data.obj;
            }).catch(error => {
                console.log(error.message)
            });
        },
        selectAll(pageNum, pageSize) {
            this.params={
                officeId:'',
                startDate:'',
                endDate:'',
                status:''
            };
            axios({
                url: `manager/Work/selectPage/${pageNum}/${pageSize}`,
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
                this.params.officeId = treeNode.id
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
        toUpdate(obj) {
            layer.obj=obj;
            layer.open({
                type:2,    //弹出  frame 窗口
                title:'更新app',
                area:['80%','80%'],  //宽,高   比例是占据父窗口的比例
                content:'manager/Work/toUpdate',
            })
        },
        toPrint(obj) {
            layer.obj=obj;
            layer.open({
                type:2,    //弹出  frame 窗口
                title:'打印五联单',
                area:['95%','95%'],  //宽,高   比例是占据父窗口的比例
                content:'manager/Work/toPrint',
            })
        },
    },
    created() {
        this.selectPage(1,this.pageInfo.pageSize)
    }
    , mounted(){
        this.initTree()
    }
})
