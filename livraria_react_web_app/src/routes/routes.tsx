import React from 'react';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import Menu from '../components/Menu';
import Home from '../pages/Home';

const Routes: React.FC = () => {

    return (
        <BrowserRouter>
            <Switch>
                <Route path='/' exact component={Menu} />
            </Switch>
        </BrowserRouter>
    )
}

export default Routes;