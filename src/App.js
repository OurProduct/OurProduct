import { Route, Routes } from 'react-router-dom';
import Main from './components/main/Main.jsx';
import RouteShowCase from './components/routing/routeShowcase/RouteShowCase.jsx';
import RouteMedia from './components/routing/routeMedia/RouteMedia.jsx';
import RouteClub from './components/routing/routecClub/RouteClub.jsx';
import RouteEventCalendar from './components/routing/routeEventCalendar/RouteEventCalendar.jsx';
import RouteFAQ from './components/routing/routeFaq/RouteFAQ.jsx';
import RouteAboutUs from './components/routing/routeAboutUs/RouteAboutUs.jsx';
import RouteEntrance from './components/routing/routeEntrance/RouteEntrance.jsx';
import RouteStartSelling from './components/routing/routeStartSelling/RouteStartSelling.jsx';
const App = () => {
  return (
    <div>
      <Routes>
        <Route path='/' element={<Main />} />
        <Route path='/showcase' element={<RouteShowCase />} />
        <Route path='/media' element={<RouteMedia />} />
        <Route path='/club' element={<RouteClub />} />
        <Route path='/event-calendar' element={<RouteEventCalendar />} />
        <Route path='/faq' element={<RouteFAQ />} />
        <Route path='/about-us' element={<RouteAboutUs />} />
        <Route path='/entrance' element={<RouteEntrance />} />
        <Route path='/start-selling' element={<RouteStartSelling />} />
      </Routes>
    </div>
  );
}

export default App;
