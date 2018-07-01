<?php
header("Content-Type: application/json;charset=utf-8");
$personID   = $_GET['personID'];
$personname        = $_GET['personname'];
$PlaceID   = $_GET['placeID'];
$comment        = $_GET['comment'];
$user_name = "id1015576_placeitnowapp";
$password  = "place311311place";
$database  = "id1015576_placeitnow";
$server          = "localhost";
$conn = new mysqli($server , $user_name , $password,$database );
$sql       = "INSERT INTO `Comments`(`PlaceID`, `PersonID`, `Comment`,  `PersonName`) VALUES( '".$PlaceID."','".$personID."','".$comment."','".$personname."')";
$result    = $conn->query($sql);

if ($result) {
          $myObj->status = 'true';

    } else {
        
        $myObj->status = 'false';
        

    }

		echo json_encode($myObj, JSON_PRETTY_PRINT);

?>