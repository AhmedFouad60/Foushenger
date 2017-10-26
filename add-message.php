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

     if(!isset($obj->{'room_id'})){

         print"{\"status\":0,\"message\":\"Room id is Missing !\"}";

    }else if(!isset($obj->{'user_id'})){

         print"{\"status\":0,\"message\":\"User id is Missing !\"}";


    }else if(!isset($obj->{'user_name'})){

         print"{\"status\":0,\"message\":\"User name is Missing !\"}";
    }else if(!isset($obj->{'type'})){

         print"{\"status\":0,\"message\":\"type  is Missing !\"}";
    }else if(!isset($obj->{'content'})){

         print"{\"status\":0,\"message\":\"Content is Missing !\"}";
    }else{
        //Everythings ok




        $room_id=$obj->{'room_id'};
        $user_id=$obj->{'user_id'};
        $user_name=$obj->{'user_name'};
        $type=$obj->{'type'};
        $content=$obj->{'content'};
        //make query using marei db
        $insertnewmessage= $db->insert('Messages',[
            'room_id'=>$room_id,
            'user_id'=>$user_id,
            'user_name'=>$user_name,
            'type'=>$type,
            'content'=>$content,
        ]);

        if($insertnewmessage){

         print"{\"status\":1,\"message\":\"Room Add Successfully !\"}"; //response to the Android with status and Message
        }else{

         print"{\"status\":0,\"message\":\"Error While adding room\"}"; //response to the Android with status and Message
            }
    }//End of Every things OK

