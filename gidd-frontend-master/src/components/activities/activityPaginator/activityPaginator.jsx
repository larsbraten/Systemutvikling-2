import ReactPaginate from "react-paginate";
import RArrow from "../right-arrow.svg";
import LArrow from "../left-arrow.svg";
import "./activityPaginator.css";
import Pagination from "@material-ui/lab/Pagination";

const renderPaginator = (props) => {
  return (
    <>
      {/** <ReactPaginate
        previousLabel={
          <img className="pagination-previous" src={LArrow} alt="left-arrow" />
        }
        nextLabel={
          <img className="pagination-next" src={RArrow} alt="right-arrow" />
        }
        breakLabel={"..."}
        breakClassName={"pagination-break"}
        pageCount={props.activities.totalPages}
        marginPagesDisplayed={2}
        pageRangeDisplayed={5}
        onPageChange={props.handlePageClick}
        containerClassName={"pagination-container"}
        pageClassName={"pagespagination"}
        activeClassName={"pagination-active"}
        previousClassName={"pagination-previous"}
        nextClassName={"pagination-next"}
        activeLinkClassName={"pagination-active-page"}
        nextLinkClassName={"pagination-active"}
        previousLinkClassName={"pagination-active"}
        pageLinkClassName={"pagination-active"}
      /> */}
      <div className="pagination-container">
        <Pagination
          count={props.activities.totalPages}
          onChange={props.handlePageClick}
        />
      </div>
    </>
  );
};

export default renderPaginator;
