import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { createEntity, getEntity, reset, updateEntity } from './app-user.reducer';

export const AppUserUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const appUserEntity = useAppSelector(state => state.appUser.entity);
  const loading = useAppSelector(state => state.appUser.loading);
  const updating = useAppSelector(state => state.appUser.updating);
  const updateSuccess = useAppSelector(state => state.appUser.updateSuccess);

  const handleClose = () => {
    navigate('/app-user');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    values.registeredDate = convertDateTimeToServer(values.registeredDate);
    values.lastLoginDate = convertDateTimeToServer(values.lastLoginDate);

    const entity = {
      ...appUserEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          registeredDate: displayDefaultDateTime(),
          lastLoginDate: displayDefaultDateTime(),
        }
      : {
          ...appUserEntity,
          registeredDate: convertDateTimeFromServer(appUserEntity.registeredDate),
          lastLoginDate: convertDateTimeFromServer(appUserEntity.lastLoginDate),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="myjhappApp.appUser.home.createOrEditLabel" data-cy="AppUserCreateUpdateHeading">
            <Translate contentKey="myjhappApp.appUser.home.createOrEditLabel">Create or edit a AppUser</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="app-user-id"
                  label={translate('myjhappApp.appUser.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('myjhappApp.appUser.externalUserId')}
                id="app-user-externalUserId"
                name="externalUserId"
                data-cy="externalUserId"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('myjhappApp.appUser.username')}
                id="app-user-username"
                name="username"
                data-cy="username"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('myjhappApp.appUser.firstName')}
                id="app-user-firstName"
                name="firstName"
                data-cy="firstName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('myjhappApp.appUser.lastName')}
                id="app-user-lastName"
                name="lastName"
                data-cy="lastName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('myjhappApp.appUser.email')}
                id="app-user-email"
                name="email"
                data-cy="email"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('myjhappApp.appUser.registeredDate')}
                id="app-user-registeredDate"
                name="registeredDate"
                data-cy="registeredDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('myjhappApp.appUser.lastLoginDate')}
                id="app-user-lastLoginDate"
                name="lastLoginDate"
                data-cy="lastLoginDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/app-user" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default AppUserUpdate;
