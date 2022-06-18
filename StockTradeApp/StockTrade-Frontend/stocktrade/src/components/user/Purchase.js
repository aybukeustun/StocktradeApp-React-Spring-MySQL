import React, { useState, useEffect } from "react";

import UserService from "../../services/user.service";
import StockService from "../../services/stock.service";
import auth from "../../services/auth.service";
import EventBus from "../../EventBus";
import userService from "../../services/user.service";





const Purchase = () => {
    
  
    const [stocksTmp,setStocksTmp] = useState([]);
    const [loadFailure, setLoadFailure] = useState(false);
    const [pendingApiCall,setPendingApiCall] = useState(false);
    const [stocks, setStocks] = useState([]);
    const [message,setMessage] = useState("");
    const [errors, setErrors] = useState("");
    const [selectedStock, setSelectedStock] = useState(
        {
            userId:auth.getCurrentUser().id,
            code:"           ",
            stockCount:null,
            price:"          ",
            date:null
        });
        const initialState = [{
            name: 'candle',
            data: []
        }];
    
        const [series,setSeries] = useState([{
            name: 'candle',
            data: []
        }]);
        const date =new Date();

 
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


 
    const onChangeCount = event => {
        setSelectedStock({...selectedStock, stockCount: event.target.value})
    }
    const colorPicker = (number) => {
        if(number>0){
            return (`#008000`);
        }
        if(number<0){
            return `#ff0000`;
        }
        if(number===0){
            return `#79817D`;
        }
    }
    
    useEffect(async ()  => {
        setLoadFailure(false);
        
        try {
            const data = await StockService.getAllStocks();
            await setStocksTmp(data);
        } catch (error) {
            setLoadFailure(true);
        }
    }, []);




    const findStock =(stockId)=>{
        
        userService.findStock(stockId).then(
            () => {
                setMessage("Succes");
                console.log("giriş");
            },
            (error) => {
                const errors =
                    (error.response &&
                        error.response.data &&
                        error.response.data.message) ||
                    error.message ||
                    error.toString();
                setErrors(errors);
            }
        );
    }
        
        
    const onfindStock = async (stock) =>{
        setSelectedStock(
            {
                ...selectedStock,
                price: stock.price,
                code: stock.code,
                date: stock.date
            }
    )
        await setSeries(initialState);
        findStock(stock.id);
    }

    

    
    const onBuy = () => {
        const {userId, code, stockCount, price, date} = selectedStock;
        UserService.buy(userId, code, stockCount, price, date).then(
            () => {
                setMessage("Succes");
            },
            (error) => {
                const errors =
                    (error.response &&
                        error.response.data &&
                        error.response.data.message) ||
                    error.message ||
                    error.toString();
                setErrors(errors);
            }
        );

    }

    const onSell = () => {
        const {userId, code, stockCount, price, date} = selectedStock;
        UserService.sell(userId, code, stockCount, price, date).then(
            () => {
                setMessage("Succes");
            },
            (error) => {
                const errors =
                    (error.response &&
                        error.response.data &&
                        error.response.data.message) ||
                    error.message ||
                    error.toString();

                setErrors(errors);
            }
        );

    }



    return (

           
        <div className="card w-125 bg-light border-dark mb-4">
            <div className="row no-gutters">
                <div className="col-md-4">
                    <div className="list-group">
                        {stocks.map((value, index) => (
                            <button className="btn btn-outline-dark btn-lg btn-block btn-lg"
                                    disabled={pendingApiCall} role="button" aria-pressed="true"
                                    onClick={(preventDefault) =>
                                        onfindStock(value)
                                    }>

                                <div className="container">
                                    <div className="row">
                                        <div className="col-4" style={{fontWeight: "bold", fontSize: 12}}>
                                            <div className="row align-items-start">
                                                <div className="col">
                                                    {pendingApiCall && (
                                                        <span className="spinner-border spinner-border-sm"></span>)}
                                                    <smail>{value.code}</smail>
                                                </div>
                                            </div>
                                            <div className="row align-items-center">
                                                <div className="col">
                                                    <br/>
                                                </div>
                                            </div>
                                            <div className="row align-items-end">
                                                <div className="col">
                                                    <small style={{fontSize: 14}}>{value.price}</small>
                                                </div>
                                            </div>
                                        </div>

                                        <div className="col-8">
                                            <div className="row align-items-start">
                                                <div className="col">
                                                    <small style={{fontSize: 18}}>{value.name}</small>
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
            <div className="col-md-8">
                <div className="row align-items-start">
                    
                </div>
                <div className="container-fluid" style={{width:"850px"}}>
                    <div className="row align-items-start">
                        <div className="card">
                        <table>
                            <thead>
                            <tr>
                                <th style={{textAlign:"center"}}><img src="https://w7.pngwing.com/pngs/47/847/png-transparent-shopping-cart-computer-icons-service-cart-angle-service-grocery-store.png" width={35} height={35}/></th>
                                <th>DATE</th>
                                <th>CODE</th>
                                <th>PRICE</th>
                                <th>COUNT</th>
                                <th>BUY</th>
                                <th>SELL</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <th  style={{textAlign:"center"}}>PURCHASE</th>
                                <th>{new Intl.DateTimeFormat("en-GB", {
                                    year: "numeric",
                                    month: "long",
                                    day: "2-digit"
                                }).format(date)}</th>
                                <th>{selectedStock.code}</th>
                                <th>{selectedStock.price}</th>
                                <th><input className="small" type="number" min={0} onChange={onChangeCount}/> </th>
                                <th><button className="btn btn-success" onClick={onBuy}>BUY</button></th>
                                <th><button className="btn btn-danger" onClick={onSell}>SELL</button></th>

                            </tr>
                            </tbody>

                        </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </div>
       

        
        
    )
}



export default Purchase;
