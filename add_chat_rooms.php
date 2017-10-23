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

     if(!isset($obj->{'room_name'})){

         print"{\"status\":0,\"message\":\"Room Name is Missing !\"}";

    }else if(!isset($obj->{'room_desc'})){

         print"{\"status\":0,\"message\":\"Room Description is Missing !\"}";

    }else{
        //Everythings ok
        $roomname=$obj->{'room_name'};
        $roomdesc=$obj->{'room_desc'};
        //make query using marei db
        $insertnewroom= $db->insert('chat_rooms',[
            'room_name' => $roomname,
            'room_desc' => $roomdesc
        ]);

        if($insertnewroom){

         print"{\"status\":1,\"message\":\"Room Add Successfully !\"}"; //response to the Android with status and Message
        }else{

         print"{\"status\":0,\"message\":\"Error While adding room\"}"; //response to the Android with status and Message
            }
    }//End of Every things OK

