const updateUrl = (queryOptions) => {
    console.log(queryOptions)
    if (!queryOptions) {
      return;
    }
    const currentQuery = qs.parse(window.location.search);
    console.log(currentQuery)
    // Apply any updates passed in over the current query. This requires consumers to explicitly
    // pass in parameters they want to remove, such as resetting the page when sorting, but ensures
    // that we bring forward all other params such as feature flags
    const newQuery = {
      ...currentQuery,
      ...queryOptions,
    };
  
    // Because we show page 1 by default, theres no reason to set the url to page=1
    if (newQuery.page === 1) {
      newQuery.page = undefined;
    }
    console.log(newQuery);
    const newQueryString = `?${qs.stringify(newQuery)}`;
    console.log(newQueryString);
    if (newQueryString !== window.location.search) {
      history.push(newQueryString);
    }
  };