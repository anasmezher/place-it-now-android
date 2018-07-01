<?php
$imgname     = $_POST['ImageName'];
$imagename2     = $_POST['ImageName'];
$ID        = $_POST['ID'];
$placeID   = $_POST['placeID'];
$user_name = "id1015576_placeitnowapp";
$password  = "place311311place";
$database  = "id1015576_placeitnow";
$server          = "localhost";
$conn = new mysqli($server , $user_name , $password,$database );

$imsrc = str_replace(' ','+',$_POST['base64']);
$imsrc = base64_decode($imsrc);
$fp = fopen($imgname, 'w');
fwrite($fp, $imsrc);
if(fclose($fp)){
	$sql       = "INSERT INTO `Images`( `PersonID`, `PlaceID`, `imagename`) VALUES( '".$ID."','".$placeID."','".$imagename2."')";
$result    = $conn->query($sql);

if ($result) {
          echo 'true';

    } else {
        
        echo'false';
        

    }

}


?>
