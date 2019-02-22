<?php

require_once('PHPMailer-master/src/PHPMailer.php');

use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\Exception;

$path = 'doacao.xml';

$myfile = fopen($path, "w") or die("Unable to open file!");
$txt = '<?xml version="1.0"?>
<doacao>
    <nome>'. $_POST["nome"] .'</nome>
    <instituicao>'. $_POST["instituicao"] .'</instituicao>
    <email>'. $_POST["email"] .'</email>
    <telefone>'. $_POST["telefone"] .'</telefone>
    <endereco>'. $_POST["endereco"] .'</endereco>
    <referencia>'. $_POST["referencia"] .'</referencia>
    <bairro>'. $_POST["bairro"] .'</bairro>
    <conteudo>'. $_POST["conteudo"] .'</conteudo>
    <conhece>'. $_POST["conhece"] .'</conhece>
    <complementares>'. $_POST["complementares"] .'</complementares>
</doacao>';
fwrite($myfile, $txt);
fclose($myfile);

$email = new PHPMailer();
$email->From      = 'ALGUM EMAIL';
$email->FromName  = 'ALGUEM';
$email->Subject   = 'Nova Doa&#231;&#227;o';
$email->AddEmbeddedImage('Emaus_Amor-e-Justica_banner.jpg', 'Emaus', 'Emaus_Amor-e-Justica_banner.jpg');
$email->Body      = '
    <html>
    <head>
    <title>HTML email</title>
    <style>
        #conteudo div{
            display: none;
            margin: auto;
        }

        .one{
            width: 50%;
        }

        .two{
            width: 50%;
        }
        
        table {
            font-family: arial, sans-serif;
            border-collapse: collapse;
            width: 100%;
        }

        td, th {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
        }

        tr:nth-child(even) {
            background-color: #dddddd;
        }

    </style>
    </head>
    <body style="margin: auto; width: 990px;">
        <p>A table as email</p>
        <img src="cid:Emaus"></img>
        <table style="margin: auto; width: 100%;">
            <tbody>
                <tr><th class="one"> Contato </th></tr>
                <tr><td class="one"> Nome: </td>
                    <td class="two">' . $_POST["nome"] . '</td></tr>
                <tr><td class="one"> Instituição (Empresa, Instituição pública, ONG, ...): </td>
                    <td class="two">' . $_POST["instituicao"] . '</td></tr>
                <tr><td class="one"> Email: </td>
                    <td class="two">' . $_POST["email"] . '</td></tr>
                <tr><td class="one"> Telefone: </td>
                    <td class="two">' . $_POST["telefone"] . '</td></tr>

                <tr><th class="one"> Endereço de coleta </th></tr>
                
                <tr><td class="one"> Endereço (Rua, n°, apto., ...): </td>
                    <td class="two">' . $_POST["endereco"] . '</td></tr>
                <tr><td class="one"> Ponto de referência: </td>
                    <td class="two">' . $_POST["referencia"] . '</td></tr>
                <tr><td class="one"> Bairro de Fortaleza:</td>
                    <td class="two">' . $_POST["bairro"] . '</td></tr>

                <tr><th class="one"> Conteúdo da doação </th></tr>

                <tr><td class="one"> Quais são os objetos que você quer doar?:</td>
                    <td class="two">' . $_POST["conteudo"] . '</td></tr>

                <tr><th class="one"> Relacionamento com o Emaús </th></tr>

                <tr><td class="one"> Como você conheceu o movimento Emaús Amor e Justiça?: </td>
                    <td class="two">' . $_POST["conhece"] . '</td></tr>
                <tr><td class="one"> Observações complementares </td>
                    <td class="two">' . $_POST["complementares"] . '</td></tr>
            </tbody>
        </table>
    </body>
</html>';
$email->IsHTML(true);  
$email->AddAddress('ALGUM EMAIL');
$email->AddAttachment('doacao.xml');
$email->Send();

if (file_exists($path)){
    if(unlink($path)){   
        echo "success";
    }else{
        echo "fail";    
    }   
} 
else{
    echo "file does not exist";
}
?>
