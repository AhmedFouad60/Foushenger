       <?php

    include 'DB.php';
    //get content input form Android to store it in the body  And create json
    //object to parse it

    $data=file_get_contents("php://input");
    $obj=json_decode($data);
    //create db instance to use marei db queris
    $db=DB::getInstance();
    //set type of the header response to application/json for response
    header('content-type: application/json');

    //check if data sent and available from

     if(!isset($obj->{'email'})){

         print"{\"status\":0,\"message\":\"email is Missing !\"}";

    }else if(!isset($obj->{'password'})){

         print"{\"status\":0,\"message\":\"password is Missing !\"}";

    }else{
        //Everythings ok
        $usermail=$obj->{'email'};
        $userpassword=$obj->{'password'};
        //i want the user register with unique email
        $check_user_email= $db->table('FoushengerUsers')
                                         ->where('user_email','=',$usermail)
                                         ->where('user_password','=',$userpassword)
                                         ->get();

        if($db->getCount()>0){

         print"{\"status\":1,\"message\":\"Welcome !\"}"; //response to the Android with status and Message
        }else{

         print"{\"status\":0,\"message\":\"Error in Email or Password\"}"; //response to the Android with status and Message
            }
    }//End of Every things OK

