import jwt_decode from 'jwt-decode';
 export class AuthResponseModel {

    constructor (

     public   email:  string,
     public   userId: string,
       private role : string ,
      private  _token : string,
      private  tokenExpirationDate : Date,
   

    ){}

    // incase token expires while he is on website 
 get token(){

    try {
        const decodedToken: { sub: string, exp: number, roles: string[] } = jwt_decode(this._token);
            this.tokenExpirationDate   = new Date(decodedToken.exp * 1000);
          // const email = decodedToken.sub;
          // const role = decodedToken.roles[0];  
    
        if (!this.tokenExpirationDate ||  new Date() > this.tokenExpirationDate  )  {
          console.log("Token session expired.");
          return null;
        } else 

        return this._token;
        
      } catch (error) {
        console.error('Error decoding JWT token:', error);
        return null;
      } 
 }

get userRole (){
  return this.role; 
}
 
    
}



