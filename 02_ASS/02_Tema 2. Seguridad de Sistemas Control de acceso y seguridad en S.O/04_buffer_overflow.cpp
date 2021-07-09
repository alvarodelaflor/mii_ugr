#include iostream
#include cstring

using namespace std;

int main()
{
 int autenticacion = 0;
 char cUsuario[ 10 ];
 char cPassword[ 10 ];

 cout  Usuario ;
 cin  cUsuario;

 cout  Password ;
 cin  cPassword;

 if( strcmp( cUsuario, root ) == 0 && strcmp( cPassword, rootpass ) == 0 )
    autenticacion = 1;
 
 if( autenticacion )
    cout  Acceso obtenido  endl;
 else
    cout  Nombre de usuario yo password incorrectos  endl;

 return 0;
}