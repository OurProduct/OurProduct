import { Route, Routes } from 'react-router-dom';
import Main from './components/main/Main';
import ShowCase from './components/routing/routeShowcase/RouteShowCase';
import Media from './components/routing/routeMedia/RouteMedia';
import Club from './components/routing/routecClub/RouteClub';
import EventCalendar from './components/routing/routeEventCalendar/RouteEventCalendar';
import FAQ from './components/routing/routeFaq/RouteFAQ';
import RouteAboutUs from './components/routing/routeAboutUs/RouteAboutUs';
import RouteEntrance from './components/routing/routeEntrance/RouteEntrance';
import RouteStartSelling from './components/routing/routeStartSelling/RouteStartSelling';

const App = () => {
  return (
    <div>
      <Routes>
        <Route path='/' element={<Main />} />
        <Route path='/showcase' element={<ShowCase />} />
        <Route path='/media' element={<Media />} />
        <Route path='/club' element={<Club />} />
        <Route path='/event-calendar' element={<EventCalendar />} />
        <Route path='/faq' element={<FAQ />} />
        <Route path='/about-us' element={<RouteAboutUs />} />
        <Route path='/entrance' element={<RouteEntrance />} />
        <Route path='/start-selling' element={<RouteStartSelling />} />
      </Routes>
    </div>
  );
}

export default App;
