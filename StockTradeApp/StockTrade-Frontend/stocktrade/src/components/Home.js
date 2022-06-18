import React, { useState, useEffect } from "react";
import StockService from "../services/stock.service";
import EventBus from "../EventBus";
import  "./Home.css";


const Home = () => {
    
    
    
    
    const [errors, setErrors] = useState("");
    const [stocks, setStocks] = useState([]);

    const [pendingApiCall,setPendingApiCall] = useState(false);



    useEffect(() => {
        StockService.getAllStocks().then(
            (data) => {
                setStocks(data);
                console.log(data);
            },
            (error) => {
                console.log(error);
                const errors =
                    (error.response &&
                        error.response.data &&
                        error.response.data.message) ||
                    error.message ||
                    error.toString();

                setErrors(errors);

                if (error.response && error.response.status === 401) {
                    EventBus.dispatch("logout");
                }
            }
        );
    });


    const onfindStock = async (stock) =>{
       
    }

    


   


  return (
      
   <div>
        <div className="title">
           WELCOME TO STOCKTRADE APPLICATION
           
       </div>
       <div className="home">
           
           <div className="left">
           
              <div className="list-group" >
                  {stocks.map((value, index) => (
                      <button className="btn btn-outline-dark btn-lg btn-block btn-lg"
                          disabled={pendingApiCall} role="button" aria-pressed="true"
                          onClick={(preventDefault) => onfindStock(value)}>

                          <div className="container">
                              <div className="row">
                                  <div className="col-4" style={{ fontWeight: "bold", fontSize: 12 }}>
                                      <div className="row align-items-start">
                                          <div className="col">
                                              {pendingApiCall && (
                                                  <span className="spinner-border spinner-border-sm"></span>)}
                                              <smail>{value.code}</smail>
                                          </div>
                                      </div>
                                      <div className="row align-items-center">
                                          <div className="col">
                                              <br />
                                          </div>
                                      </div>
                                      <div className="row align-items-end">
                                          <div className="col">
                                              <small style={{ fontSize: 14 }}>{value.price}</small>
                                          </div>
                                      </div>
                                  </div>

                                  <div className="col-8">
                                      <div className="row align-items-start">
                                          <div className="col">
                                              <small style={{ fontSize: 18 }}>{value.name}</small>
                                          </div>
                                      </div>
                                      <div className="row align-items-end">

                                      </div>
                                  </div>
                              </div>
                          </div>
                      </button>
                  ))}
              
          </div>
           </div>
           <div className="right">
          
       <img src="https://cdn.discordapp.com/attachments/781160413710385165/891789657724043274/Untitled-1.png" />
           </div>
      

          
      </div>
     </div>
     


   
  )
};

export default Home;