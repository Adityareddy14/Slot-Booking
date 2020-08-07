var express = require("express");
var mysql = require('mysql');
var bodyParser = require("body-parser");

var app = express();

var con = mysql.createConnection({
   host:'localhost',
   user:'root',
   password:'1410',
   database:'sys'
});
con.connect(function(err) {
    if (err) throw err;
    console.log("Connected!");
  });

app.use(bodyParser.urlencoded());
app.use(bodyParser.urlencoded({extended:true}));
app.use(bodyParser.json());


app.post('/bookslot',function(req,res,next){
  var post_data = req.body;
  var user_name = post_data.username;
  var email = post_data.email;
  var num;


  con.query('select count(statu_s) as c from user where statu_s="true"', function(err,rows,fileds){
    if(err) throw err;
   // console.log(rows[0].c);
    num = rows[0].c+1;
  });
  

  con.query('SELECT * FROM user where username=?',[user_name],function(err,result,fields){

      if(err){
          console.log(err)
      }

     if(result && result.length)
     {
        if((email == result[0].email)){
                if(result[0].statu_s!='true'){
                    if((num<6)){
                        var sql = "update user set statu_s='true' where email = '"+email+"';"
                        con.query(sql,function(err,result){
                        if (err)
                        console.log(err)
                        res.end(JSON.stringify('Booking confirmed from 5-6pm'));
                        })
                    }
                    else{
                            res.end(JSON.stringify('This slot is full. Please try again later'));
                        }
                }
                else{
                    res.end(JSON.stringify('Already booked the slot from 5-6pm'));
                }    
         
         
        }
        else{
            res.end(JSON.stringify('Enter correct credentials'));
        } 
      
     }

     else
     {
      res.json("User not exists!!");
     }
      
   });

})


app.listen(6000,()=>{
    console.log('Restful running on port 6000');
})


