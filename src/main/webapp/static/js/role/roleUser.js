let vm = new Vue({
    el: '.main-content',
    data() {
        return{
            ysIds:[],
            dsIds:[],
            showYc:0,
            showDs:0,
            oid:'',
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
            ysUser:{},
            xdUser:{}
        }
    },
    methods: {
        doYs(){
            let params = {rid:parent.layer.rid,ids:this.ysIds+''};
            axios({
                url:'manager/role/deleteBatch',
                params:params
            }).then(response => {
               if (response.data.success){
                   this.selectByRid(parent.layer.rid);
                   this.ysIds=[];
                   this.showYc=0
               }
                // this.yxUser();
                // this.showRemoveBtn='hide';
                // this.yxIds=[];
                // if(this.treeNode!=undefined){
                //     this.dxUser();//更新待选人员
                // }

            }).catch(function (error) {
                layer.msg(error.message);
            })
        },
        doDx(){
            let params = {rid:parent.layer.rid,cids:this.dsIds+''};
            axios({
                url:'manager/role/insertBatch',
                params:params
            }).then(response => {

                this.selectNoRole(parent.layer.rid,this.oid);
                this.dsIds=[];
                this.selectByRid(parent.layer.rid);//更新待选人员
                this.showDs=0;

            }).catch(function (error) {
                layer.msg(error.message);
            })
        },
        checked(params,rid){
            if (params.toElement.checked){
                this.showYc+=1;
                this.ysIds.push(rid)
            }
            else {
                this.showYc-=1;
                //使用some方法删除相应下标的数组
                this.ysIds.some((item,i)=>{
                    if (item===rid){
                        this.ysIds.splice(i,1);
                        return true
                    }
                })
            }
        },
        dschecked(params,rid){
            if (params.toElement.checked){
                this.showDs+=1;
                this.dsIds.push(rid)
            }
            else {
                this.showDs-=1;
                //使用some方法删除相应下标的数组
                this.dsIds.some((item,i)=>{
                    if (item===rid){
                        this.dsIds.splice(i,1);
                        return true
                    }
                })
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
            // if (treeNode.id!=0) {
            //     this.params.oid = treeNode.id
            // }
            this.selectNoRole(parent.layer.rid,treeNode.id)
        },
        initTree() {
            axios({
                url:'manager/Work/office',
                method:'post',
            }).then( resp =>{
                this.nodes = resp.data.obj;
                //动态生成一个根节点
                this.nodes[this.nodes.length]={id:0,name:'全部机构',open:true};
                let zTree = $.fn.zTree.init($('#treeOffice'), this.setting, this.nodes);
            } ).catch( error =>{
                console.log(error.message)
            } )

        },
        fontCss(treeId,treeNode) {
            /*根据高亮标记设置高亮红色*/
            return treeNode.hightLight?{color:'red'}:{color:'black'};
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
        selectByRid(id){
            axios({
                url:`manager/user/selectByRid/${id}`
            }).then(resp=>{
                this.ysUser = resp.data.obj;
                
            }).catch(error=>{

            })
        },
        selectNoRole(rid,oid){
            this.oid=oid;
            axios({
                url:`manager/user/selectNoRole`,
                params:{
                    rid:rid,
                    oid:oid
                }
            }).then(resp=>{
                this.xdUser = resp.data.obj;

            }).catch(error=>{

            })
        }
    },
    created() {
        this.selectByRid(parent.layer.rid)
    },
    mounted(){
        this.initTree();
    }
})
