# React Query

- Library for fetching data in React applications
- No specific pattern for fetching data in react. Usually useEffect
- Client state vs Server State:
  - client state can be managed via state management libraries (redux) -> theme, modal open
  - server state is difficult to manage with those. So react query is best used for these -> api data
- Query and Mutations

## React Query Setup

- Wrap application with `QueryClientProvider` with a new queryClient instance
- To fetch data use `useQuery` hook
