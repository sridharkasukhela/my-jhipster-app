import React, { useEffect, useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { TextFormat, Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './app-user.reducer';

export const AppUser = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const appUserList = useAppSelector(state => state.appUser.entities);
  const loading = useAppSelector(state => state.appUser.loading);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        sort: `${sortState.sort},${sortState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?sort=${sortState.sort},${sortState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [sortState.order, sortState.sort]);

  const sort = p => () => {
    setSortState({
      ...sortState,
      order: sortState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = sortState.sort;
    const order = sortState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    }
    return order === ASC ? faSortUp : faSortDown;
  };

  return (
    <div>
      <h2 id="app-user-heading" data-cy="AppUserHeading">
        <Translate contentKey="myjhappApp.appUser.home.title">App Users</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="myjhappApp.appUser.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/app-user/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="myjhappApp.appUser.home.createLabel">Create new App User</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {appUserList && appUserList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="myjhappApp.appUser.id">Id</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('externalUserId')}>
                  <Translate contentKey="myjhappApp.appUser.externalUserId">External User Id</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('externalUserId')} />
                </th>
                <th className="hand" onClick={sort('username')}>
                  <Translate contentKey="myjhappApp.appUser.username">Username</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('username')} />
                </th>
                <th className="hand" onClick={sort('firstName')}>
                  <Translate contentKey="myjhappApp.appUser.firstName">First Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('firstName')} />
                </th>
                <th className="hand" onClick={sort('lastName')}>
                  <Translate contentKey="myjhappApp.appUser.lastName">Last Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('lastName')} />
                </th>
                <th className="hand" onClick={sort('email')}>
                  <Translate contentKey="myjhappApp.appUser.email">Email</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('email')} />
                </th>
                <th className="hand" onClick={sort('registeredDate')}>
                  <Translate contentKey="myjhappApp.appUser.registeredDate">Registered Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('registeredDate')} />
                </th>
                <th className="hand" onClick={sort('lastLoginDate')}>
                  <Translate contentKey="myjhappApp.appUser.lastLoginDate">Last Login Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('lastLoginDate')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {appUserList.map((appUser, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/app-user/${appUser.id}`} color="link" size="sm">
                      {appUser.id}
                    </Button>
                  </td>
                  <td>{appUser.externalUserId}</td>
                  <td>{appUser.username}</td>
                  <td>{appUser.firstName}</td>
                  <td>{appUser.lastName}</td>
                  <td>{appUser.email}</td>
                  <td>
                    {appUser.registeredDate ? <TextFormat type="date" value={appUser.registeredDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {appUser.lastLoginDate ? <TextFormat type="date" value={appUser.lastLoginDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/app-user/${appUser.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/app-user/${appUser.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/app-user/${appUser.id}/delete`)}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="myjhappApp.appUser.home.notFound">No App Users found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default AppUser;
