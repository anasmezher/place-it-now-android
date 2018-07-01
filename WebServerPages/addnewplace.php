
<?php
error_reporting(E_ALL);
if(isset($_POST['city'])){
$city      = $_POST['city'];
$country   = $_POST['country'];
$langi     = $_POST['langi'];
$lati      = $_POST['lati'];
$details   = $_POST['details'];
$type      = $_POST['type'];
$ID        = $_POST['ID'];
$imgname = $_POST['ImageName'];
$imgname2 = $_POST['ImageName'];
$imsrc = str_replace(' ','+',$_POST['base64']);
$imsrc = base64_decode($imsrc);
$fp = fopen($imgname, 'w');
fwrite($fp, $imsrc);
if(fclose($fp)){
 $user_name = "id1015576_placeitnowapp";
$password  = "place311311place";
$database  = "id1015576_placeitnow";
$server          = "localhost";
$conn2 = new mysqli($server , $user_name , $password,$database ); 
     $sql1 = "INSERT INTO `places`(`PlaceName`, `PlaceLocationLongitude`, `PlaceLocationLatitude`, `Type`, `VisitorID`, `imagename`) VALUES( '".$details."','".$langi."','".$lati."','".$type."','".$ID."','".$imgname2."')";
    $result2 = $conn2->query($sql1);
    if ($result2) {
		  echo "true";
    } else {
 		  echo "false";
    }

}else{
 		  echo "false";
}
}
?>

