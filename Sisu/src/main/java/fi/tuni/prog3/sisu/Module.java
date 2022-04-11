 package fi.tuni.prog3.sisu;


abstract class Module {

   private String id;
   private String name;
   
   Module(){
       
   }
   
   public String getId(){
       return this.id;
   }
   
   public String getName(){
       return this.name;
   }

}