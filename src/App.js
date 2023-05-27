import { Route, Routes } from 'react-router-dom';
import Main from './components/main/Main';
import ShowCase from './components/routing/showcase/ShowCase';

const App = () => {
  return (
    <div>
      <Routes>
        <Route path='/' element={<Main />} />
        <Route path='/showcase' element={<ShowCase />} />
        <Route path='/media' element={<ShowCase />} />
        <Route path='/club' element={<ShowCase />} />
      </Routes>
    </div>
  );
}

export default App;
