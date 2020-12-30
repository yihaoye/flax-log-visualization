import { render, screen } from '@testing-library/react';
import App from './App';

test('renders dashboard text', () => {
  render(<App />);
  const dashboardElement = screen.getByText(/dashboard/i);
  expect(dashboardElement).toBeInTheDocument();
});
