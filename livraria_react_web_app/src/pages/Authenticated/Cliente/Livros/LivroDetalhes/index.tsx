import React from 'react';
import { useParams } from 'react-router-dom';

interface paramsProps {
    id: string
}


const LivroDetalhes: React.FC = () => {

    const params = useParams<paramsProps>();

    return (
        <div>
            <div>
                <h1>{params.id}</h1>
            </div>
        </div>
    );
}

export default LivroDetalhes;