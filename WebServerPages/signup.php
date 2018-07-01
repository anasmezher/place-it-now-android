<?php
header("Content-Type: application/json;charset=utf-8");
$email     = $_GET['email'];
$passwrd   = $_GET['pass'];
$adress    = $_GET['adress'];
$phone     = $_GET['phone'];
$name      = $_GET['name'];
$gender    = $_GET['gender'];
$birthdate = $_GET['birthdate'];
$username  = $_GET['username'];
$user_name = "id1015576_placeitnowapp";
$password  = "place311311place";
$database  = "id1015576_placeitnow";
$server          = "localhost";
$conn = new mysqli($server , $user_name , $password,$database );
$sql1      = "SELECT * FROM `Visitors` where `Email`=`$email` and `Password`=`$passwrd`";
$result1   = $conn->query($sql);

if ($result1) {
    
    $myObj->status = 'false';
    
    $myJSON = json_encode($myObj);
    
    echo $myJSON;
    
} else {
    $sql    = "INSERT INTO `Visitors`( `Name`, `UserName`, `Gender`, `Phone`, `Email`, `Password`, `Birthdate`, `Adress`) VALUES( '$name','$username','$gender'
 ,'$phone','$email','$passwrd','$birthdate','$adress')";
    $result = $conn->query($sql);
    
    if ($result) {
          $myObj->status = 'true';

    } else {
        
        $myObj->status = 'false';
        

    }

		echo json_encode($myObj, JSON_PRETTY_PRINT);
 
}

?>