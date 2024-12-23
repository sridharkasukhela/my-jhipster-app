import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './user-group.reducer';

export const UserGroupDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const userGroupEntity = useAppSelector(state => state.userGroup.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="userGroupDetailsHeading">
          <Translate contentKey="myjhappApp.userGroup.detail.title">UserGroup</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="myjhappApp.userGroup.id">Id</Translate>
            </span>
          </dt>
          <dd>{userGroupEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="myjhappApp.userGroup.name">Name</Translate>
            </span>
          </dt>
          <dd>{userGroupEntity.name}</dd>
          <dt>
            <Translate contentKey="myjhappApp.userGroup.appUser">App User</Translate>
          </dt>
          <dd>{userGroupEntity.appUser ? userGroupEntity.appUser.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/user-group" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user-group/${userGroupEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default UserGroupDetail;
