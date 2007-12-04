ALTER TABLE plot_params DROP COLUMN loh_threshold;
ALTER TABLE plot_params ADD loh_threshold NUMBER;

ALTER TABLE plot_params DROP COLUMN cn_max_saturation;
ALTER TABLE plot_params ADD cn_max_saturation NUMBER;

ALTER TABLE plot_params DROP COLUMN cn_min_saturation;
ALTER TABLE plot_params ADD cn_min_saturation NUMBER;

ALTER TABLE plot_params DROP COLUMN expr_max_saturation;
ALTER TABLE plot_params ADD expr_max_saturation NUMBER;

ALTER TABLE plot_params DROP COLUMN expr_min_saturation;
ALTER TABLE plot_params ADD expr_min_saturation NUMBER;

ALTER TABLE plot_params DROP COLUMN min_mask;
ALTER TABLE plot_params ADD min_mask NUMBER;

ALTER TABLE plot_params DROP COLUMN max_mask;
ALTER TABLE plot_params ADD max_mask NUMBER;

ALTER TABLE plot_params DROP COLUMN cn_min_y;
ALTER TABLE plot_params ADD cn_min_y NUMBER;

ALTER TABLE plot_params DROP COLUMN cn_max_y;
ALTER TABLE plot_params ADD cn_max_y NUMBER;

ALTER TABLE plot_params DROP COLUMN expr_min_y;
ALTER TABLE plot_params ADD expr_min_y NUMBER;

ALTER TABLE plot_params DROP COLUMN expr_max_y;
ALTER TABLE plot_params ADD expr_max_y NUMBER;

ALTER TABLE plot_params DROP COLUMN max_y;
ALTER TABLE plot_params ADD max_y NUMBER;

ALTER TABLE plot_params DROP COLUMN min_y;
ALTER TABLE plot_params ADD min_y NUMBER;
