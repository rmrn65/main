import styles from './app.module.css';

import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';

import HttpDemo from './services/HttpServiceDemo';

export function App() {
  return (
    <div className={styles.app}>
      <Router>
        <Switch>
          <Route exact path={'/'}>
            <HttpDemo resourceUrl='/lecture-materials'/>
          </Route>
        </Switch>
      </Router>
    </div>
  );
}
export default App;
