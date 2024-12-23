import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './app-user.reducer';

export const AppUserDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const appUserEntity = useAppSelector(state => state.appUser.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="appUserDetailsHeading">
          <Translate contentKey="myjhappApp.appUser.detail.title">AppUser</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="myjhappApp.appUser.id">Id</Translate>
            </span>
          </dt>
          <dd>{appUserEntity.id}</dd>
          <dt>
            <span id="externalUserId">
              <Translate contentKey="myjhappApp.appUser.externalUserId">External User Id</Translate>
            </span>
          </dt>
          <dd>{appUserEntity.externalUserId}</dd>
          <dt>
            <span id="username">
              <Translate contentKey="myjhappApp.appUser.username">Username</Translate>
            </span>
          </dt>
          <dd>{appUserEntity.username}</dd>
          <dt>
            <span id="firstName">
              <Translate contentKey="myjhappApp.appUser.firstName">First Name</Translate>
            </span>
          </dt>
          <dd>{appUserEntity.firstName}</dd>
          <dt>
            <span id="lastName">
              <Translate contentKey="myjhappApp.appUser.lastName">Last Name</Translate>
            </span>
          </dt>
          <dd>{appUserEntity.lastName}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="myjhappApp.appUser.email">Email</Translate>
            </span>
          </dt>
          <dd>{appUserEntity.email}</dd>
          <dt>
            <span id="registeredDate">
              <Translate contentKey="myjhappApp.appUser.registeredDate">Registered Date</Translate>
            </span>
          </dt>
          <dd>
            {appUserEntity.registeredDate ? <TextFormat value={appUserEntity.registeredDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="lastLoginDate">
              <Translate contentKey="myjhappApp.appUser.lastLoginDate">Last Login Date</Translate>
            </span>
          </dt>
          <dd>
            {appUserEntity.lastLoginDate ? <TextFormat value={appUserEntity.lastLoginDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/app-user" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/app-user/${appUserEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AppUserDetail;
