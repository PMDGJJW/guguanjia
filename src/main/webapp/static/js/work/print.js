let vm = new Vue({
    el: '.app',
    data: {
        work:{

        },
        result:{

        },
        date:[
        ]
    },
    methods: {
        selectDetail(){
            axios({
                url:`manager/Work/selectDetail/${this.work.id}`,
            }).then(resp=>{
                this.result=resp.data;
                console.log(resp.data);
                for (var i = 0; i <resp.data.transfet.length ; i++) {
                    this.date.push({
                        y: resp.data.transfet[i].createDate.substring(0, 4),
                        m: resp.data.transfet[i].createDate.substring(5, 7),
                        d:resp.data.transfet[i].createDate.substring(8,10)
                    });
                }
                console.log(this.date);
            }).catch(error=>{

            })
        }
    },
    created() {
        this.work = parent.layer.obj;
        this.selectDetail()
        console.log(parent.layer.obj);

    }
})
