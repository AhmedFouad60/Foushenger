       <?php

    include 'DB.php';
    //get content input form Android to store it in the body  And create json
    //object to parse it

    $data=file_get_contents("php://input");
    //create db instance to use marei db queris
    $db=DB::getInstance();
    //set type of the header response to application/json for response
    header('content-type: application/json');

    //check if id is sent from the client
    if(!isset($_POST['room_id'])){
        print "{\"status\":0,\"message\":\" room id is Missing !\"}";
    }else{
        //get room id rom client
        $room_id=$_POST['room_id'];
        echo $db-> table("Messages")->where('room_id',$room_id)->limit(25)->get();
    }
