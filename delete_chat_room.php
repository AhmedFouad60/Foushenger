       <?php

    include 'DB.php';
    //get content input form Android to store it in the body

    $data=file_get_contents("php://input");
    //create db instance to use marei db queris
    $db=DB::getInstance();
    //set type of the header response to application/json for response
    header('content-type: application/json');

    //check if data sent and available from

     if(!isset($_POST['id'])){

         print"{\"status\":0,\"message\":\"Room Name is NOT found !\"}";


    }else{
        //Everythings ok
        $room_id=$_POST['id'];

        //make query using marei db
        $delete_room= $db->delete("chat_rooms")->where ("id",$room_id)->exec();

        if($delete_room){

         print"{\"status\":1,\"message\":\"Room deleted Successfully !\"}"; //response to the Android with status and Message
        }else{

         print"{\"status\":0,\"message\":\"Error While deleting room\"}"; //response to the Android with status and Message
            }
    }//End of Every things OK

