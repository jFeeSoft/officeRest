SET client_encoding = 'WIN1250';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

alter database swzu set default_text_search_config = 'public.polish';
ALTER database swzu SET join_collapse_limit = 100;
ALTER database swzu SET from_collapse_limit = 100;

CREATE SCHEMA IF NOT EXISTS adm;