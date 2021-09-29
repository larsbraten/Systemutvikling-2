import Loader from "react-loader-spinner";
import "./loaderWheel.css";

export default function LoaderWheel() {
    //other logic
    return (
        <div className="loader">
            <Loader
                type="Puff"
                color="#00BFFF"
                height={100}
                width={100}
            />
        </div>
    );
}